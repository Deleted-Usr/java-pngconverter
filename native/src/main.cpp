#include <windows.h>

#include <filesystem>
#include <string>
#include <vector>

#include <jni.h>

namespace fs = std::filesystem;
typedef jint(JNICALL *CreateJavaVM_t)(JavaVM**, void**, void**);

fs::path getExecutablePath()
{
    std::vector<wchar_t> buffer(32768);

    const DWORD length = GetModuleFileNameW(
            nullptr,
            buffer.data(),
            static_cast<DWORD>(buffer.size())
    );

    if (length == 0 || length == buffer.size())
    {
        throw std::runtime_error("Could not determine executable location.");
    }

    return fs::path(
            std::wstring(buffer.data(), length)
    ).parent_path();
}

std::string buildClassPath(const fs::path& rootDir)
{
    fs::path appJar = rootDir / "app" / "PNGConverter.jar";
    fs::path libsDir = rootDir / "libs";

    if (!fs::is_regular_file(appJar))
    {
        throw std::runtime_error(
                "Application JAR was not found: "
                + appJar.string()
        );
    }

    if (!fs::is_directory(libsDir))
    {
        throw std::runtime_error(
                "Libraries directory was not found: "
                + libsDir.string()
        );
    }

    std::vector<fs::path> libs;

    for (const fs::directory_entry& entry : fs::directory_iterator(libsDir))
    {
        if (entry.is_regular_file() && entry.path().extension() == ".jar")
        {
            libs.push_back(entry.path());
        }
    }

    std::sort(libs.begin(), libs.end());

    std::string classPath = appJar.string();

    for (const fs::path& library : libs)
    {
        classPath += ";";
        classPath += library.string();
    }

    return classPath;
}

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
    // Locate the JVM DLL
    std::wstring dllPath = L"runtime\\bin\\server\\jvm.dll";
    HMODULE hJmdDll = LoadLibraryW(dllPath.c_str());
    if (!hJmdDll)
    {
        MessageBoxW(nullptr, L"Failed to load JVM.", L"Error", MB_ICONERROR);
        return 1;
    }

    // Get the address of JNI_CreateJavaVM
    CreateJavaVM_t F_CreateJavaVM = (CreateJavaVM_t) GetProcAddress(hJmdDll, "JNI_CreateJavaVM");
    if (!F_CreateJavaVM)
    {
        return 1;
    }

    JavaVM* jvm = nullptr; // Pointer to the JVM
    JNIEnv* env = nullptr; // Pointer to the native interface functions

    // Set up JVM arguments (.vmoptions)
    JavaVMInitArgs initArgs;
    JavaVMOption options[3];

    std::string classPath;
    try
    {
        fs::path rootDir = getExecutablePath();
        classPath = "-Djava.class.path=" + buildClassPath(rootDir);
    }
    catch (const std::exception& e)
    {
        MessageBoxW(nullptr, L"Failed to build ClassPath", L"Error", MB_ICONERROR);
        return 1;
    }

    options[0].optionString = (char*) classPath.c_str();
    options[1].optionString = (char*) "-Xmx512m"; // Memory Limit
    options[2].optionString = (char*) "--enable-native-access=ALL-UNNAMED"; // Native access for FlatLaf

    initArgs.version = JNI_VERSION_10;
    initArgs.nOptions = 3;
    initArgs.options = options;
    initArgs.ignoreUnrecognized = JNI_TRUE;

    // Start the JVM
    if (F_CreateJavaVM(&jvm, (void**) &env, (void**) &initArgs))
    {
        MessageBoxW(nullptr, L"Failed to initialize JVM.", L"Error", MB_ICONERROR);
        return 1;
    }

    // Find and invoke the main class
    jclass mainClass = env->FindClass("com/willclay/pngconverter/Main");
    if (mainClass)
    {
        jmethodID mainMethod = env->GetStaticMethodID(mainClass, "main", "([Ljava/lang/String;)V");
        if (mainMethod)
        {
            jobjectArray args = env->NewObjectArray(0, env->FindClass("java/lang/String"), nullptr);
            env->CallStaticVoidMethod(mainClass, mainMethod, args);
        }
    }

    // Safely Destroy the JVM
    jvm->DestroyJavaVM();
    return 0;
}