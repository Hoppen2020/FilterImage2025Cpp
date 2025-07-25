#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/imgproc/types_c.h>
#include <android/bitmap.h>

#define TAG "CoderHui lib from filterimage_skin"
//#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) 	// 定义LOGE类型

using namespace cv;

void bitmapToMat(JNIEnv *env, jobject bitmap, Mat& dst, jboolean needUnPremultiplyAlpha)
{
    AndroidBitmapInfo  bitmapInfo;
    void* pixels = nullptr;

    try {
        CV_Assert(AndroidBitmap_getInfo(env, bitmap, &bitmapInfo) >= 0 );
        CV_Assert(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                  bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565 );
        CV_Assert( AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0 );
        CV_Assert( pixels );
        dst.create(bitmapInfo.height, bitmapInfo.width, CV_8UC4);
        if(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 )
        {
            Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC4, pixels);
            if(needUnPremultiplyAlpha) cvtColor(tmp, dst, COLOR_mRGBA2RGBA);
            else tmp.copyTo(dst);
        } else {
            // bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565
            Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC2, pixels);
            cvtColor(tmp, dst, COLOR_BGR5652RGBA);
        }
        AndroidBitmap_unlockPixels(env, bitmap);
        return;
    } catch(const Exception& e) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, e.what());
        return;
    } catch (...) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, "Unknown exception in JNI code {nBitmapToMat}");
        return;
    }
}

void matToBitmap(JNIEnv* env, Mat src, jobject bitmap, jboolean needPremultiplyAlpha)
{
    AndroidBitmapInfo bitmapInfo;
    void* pixels = nullptr;

    try {
        CV_Assert(AndroidBitmap_getInfo(env, bitmap, &bitmapInfo) >= 0 );
        CV_Assert(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                  bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565 );
        CV_Assert( src.dims == 2 && bitmapInfo.height == (uint32_t)src.rows && bitmapInfo.width == (uint32_t)src.cols );
        CV_Assert( src.type() == CV_8UC1 || src.type() == CV_8UC3 || src.type() == CV_8UC4 );
        CV_Assert( AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0 );
        CV_Assert( pixels );
        if(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 )
        {
            Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC4, pixels);
            if(src.type() == CV_8UC1)
            {
                cvtColor(src, tmp, COLOR_GRAY2RGBA);
            } else if(src.type() == CV_8UC3){
                cvtColor(src, tmp, COLOR_RGB2RGBA);
            } else if(src.type() == CV_8UC4){
                if(needPremultiplyAlpha) cvtColor(src, tmp, COLOR_RGBA2mRGBA);
                else src.copyTo(tmp);
            }
        } else {
            // bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565
            Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC2, pixels);
            if(src.type() == CV_8UC1)
            {
                cvtColor(src, tmp, COLOR_GRAY2BGR565);
            } else if(src.type() == CV_8UC3){
                cvtColor(src, tmp, COLOR_RGB2BGR565);
            } else if(src.type() == CV_8UC4){
                cvtColor(src, tmp, COLOR_RGBA2BGR565);
            }
        }
        AndroidBitmap_unlockPixels(env, bitmap);
        return;
    } catch(const Exception& e) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, e.what());
        return;
    } catch (...) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, "Unknown exception in JNI code {nMatToBitmap}");
        return;
    }
}
extern "C" JNIEXPORT jstring JNICALL
Java_co_hoppen_filterimage_1hair_HairEngine_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_co_hoppen_filterimage_1hair_HairEngine_test(JNIEnv *env, jobject thiz, jobject bitmapIn,
                                                 jobject bitmapOut) {
    Mat src, greyOut;
    bitmapToMat(env, bitmapIn, src, false);
    cvtColor(src, greyOut, CV_BGR2HSV);
    matToBitmap(env, greyOut, bitmapOut, false);
}