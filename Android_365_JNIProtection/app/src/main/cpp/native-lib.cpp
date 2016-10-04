#include <jni.h>
#include <string>
#include <Android/log.h>

#define DebugTag "Jni_Debug"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,DebugTag ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,DebugTag ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,DebugTag ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,DebugTag ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,DebugTag ,__VA_ARGS__)


#define Correct_Signature "YourSignatureHere"


extern "C"
jstring
Java_idv_chauyan_jniprotection_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */,
        jstring signature) {


    std::string testingSignature = env->GetStringUTFChars(signature, NULL);
    std::string CorrectSignature = Correct_Signature;

    /*
    LOGD("CorrectSignature : %s", CorrectSignature.c_str());
    LOGD("testingSignature : %s", testingSignature.c_str());
     */

    if (testingSignature.compare(0, CorrectSignature.length(), CorrectSignature) == 0)
        return env->NewStringUTF("Correct");
    else
        return env->NewStringUTF("Incorrect");
}
