#include <jni.h>
#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jstring JNICALL Java_com_bpj_optimization_optimization_JniUtil_compressBitmap(JNIEnv*,
		jclass, jobject , int , int , int ,
		jbyteArray , jboolean );

#ifdef __cplusplus
}
#endif