#include <stdio.h>
#include <setjmp.h>
#include <math.h>
#include <stdint.h>
#include <time.h>
#include "jpeglib.h"
#include "cdjpeg.h"		/* Common decls for cjpeg/djpeg applications */
#include "jversion.h"		/* for version message */
#include "android/config.h"

typedef uint8_t BYTE;

#define true 1
#define false 0
char *error;
struct my_error_mgr {
  struct jpeg_error_mgr pub;
  jmp_buf setjmp_buffer;
};

typedef struct my_error_mgr * my_error_ptr;

METHODDEF(void)
my_error_exit (j_common_ptr cinfo)
{
  my_error_ptr myerr = (my_error_ptr) cinfo->err;
  (*cinfo->err->output_message) (cinfo);
  error=myerr->pub.jpeg_message_table[myerr->pub.msg_code];
  longjmp(myerr->setjmp_buffer, 1);
}

void jstringTostring(JNIEnv* env, jstring jstr, char * output, int * de_len) {
	*output = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "utf-8");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
			strencode);
	jsize alen = (*env)->GetArrayLength(env, barr);
	*de_len = alen;
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		output = (char*) malloc(alen + 1);
		memcpy(output, ba, alen);
		output[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
}


int generateJPEG(BYTE* data, int w, int h, int quality,
		const char* outfilename, jboolean optimize) {
	int nComponent = 3;

	struct jpeg_compress_struct jcs;

	struct my_error_mgr jem;

	jcs.err = jpeg_std_error(&jem.pub);
	jem.pub.error_exit = my_error_exit;
	    if (setjmp(jem.setjmp_buffer)) {
	        return 0;
	     }
	jpeg_create_compress(&jcs);
	FILE* f = fopen(outfilename, "wb");
	if (f == NULL) {
		return 0;
	}
	jpeg_stdio_dest(&jcs, f);
	jcs.image_width = w;
	jcs.image_height = h;
	jcs.arith_code = false;
	jcs.input_components = nComponent;
	if (nComponent == 1)
		jcs.in_color_space = JCS_GRAYSCALE;
	else
		jcs.in_color_space = JCS_RGB;

	jpeg_set_defaults(&jcs);
	jcs.optimize_coding = optimize;
	jpeg_set_quality(&jcs, quality, true);

	jpeg_start_compress(&jcs, TRUE);

	JSAMPROW row_pointer[1];
	int row_stride;
	row_stride = jcs.image_width * nComponent;
	while (jcs.next_scanline < jcs.image_height) {
		row_pointer[0] = &data[jcs.next_scanline * row_stride];

		jpeg_write_scanlines(&jcs, row_pointer, 1);
	}

	jpeg_finish_compress(&jcs);
	jpeg_destroy_compress(&jcs);
	fclose(f);

	return 1;
}

typedef struct {
	uint8_t r;
	uint8_t g;
	uint8_t b;
} rgb;

char* jstrinTostring(JNIEnv* env, jbyteArray barr) {
	char* rtn = NULL;
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, 0);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
	return rtn;
}

jbyteArray stoJstring(JNIEnv* env, const char* pat,int len) {
	jbyteArray bytes = (*env)->NewByteArray(env, len);
	(*env)->SetByteArrayRegion(env, bytes, 0, len,  pat);
	jsize alen = (*env)->GetArrayLength(env, bytes);
	return bytes;
}
