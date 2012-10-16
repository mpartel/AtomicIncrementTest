
#include "fi_helsinki_cs_atomicincrementtest_MyAtomicLongArray.h"
#include <stdlib.h>

JNIEXPORT jlong JNICALL Java_fi_helsinki_cs_atomicincrementtest_MyAtomicLongArray_allocate
  (JNIEnv *env, jobject obj, jint size)
{
    return (jlong)calloc(size, sizeof(jlong));
}

JNIEXPORT jlong JNICALL Java_fi_helsinki_cs_atomicincrementtest_MyAtomicLongArray_getFrom
  (JNIEnv *env, jobject obj, jlong addr)
{
    jlong* ptr = (jlong*)addr;
    return *ptr;
}

JNIEXPORT void JNICALL Java_fi_helsinki_cs_atomicincrementtest_MyAtomicLongArray_incAt
  (JNIEnv *env, jobject obj, jlong addr)
{
    jlong* ptr = (jlong*)addr;
    __asm__ __volatile__(
        "lock; incl (%0)\n"
        :
        : "r"(ptr)
    );
}
