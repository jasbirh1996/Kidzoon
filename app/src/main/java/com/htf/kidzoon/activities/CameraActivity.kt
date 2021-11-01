package com.htf.kidzoon.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.extensions.HdrImageCaptureExtender
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.htf.kidzoon.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class CameraActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity:Activity=this
    private val REQUEST_CODE_PERMISSIONS = 1001
    private val REQUIRED_PERMISSIONS = arrayOf(
        "android.permission.CAMERA",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )
    var cameraLanceSelector=CameraSelector.LENS_FACING_BACK
    private val executor: Executor = Executors.newSingleThreadExecutor()


    companion object{
        fun open(currActivity: Activity,requestCode: Int){
            val intent= Intent(currActivity,CameraActivity::class.java)
            currActivity.startActivityForResult(intent,requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        setListener()
        if(allPermissionsGranted()){
            startCamera(); //start camera if permission has been granted by user
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    private fun setListener() {
        frontFaceCamera.setOnClickListener(this)
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this@CameraActivity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera();
            } else{
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }

    }

    private fun startCamera() {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            try {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider)
            } catch (e: ExecutionException) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            } catch (e: InterruptedException) {
            }
        }, ContextCompat.getMainExecutor(this))
    }

    fun bindPreview(@NonNull cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cameraLanceSelector)
            .build()
        val imageAnalysis = ImageAnalysis.Builder()
            .build()
        val builder = ImageCapture.Builder()

        //Vendor-Extensions (The CameraX extensions dependency in build.gradle)
        val hdrImageCaptureExtender =
            HdrImageCaptureExtender.create(builder)

        // Query if extension is available (optional).
        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            // Enable the extension if available.
            hdrImageCaptureExtender.enableExtension(cameraSelector)
        }
        val imageCapture = builder
            .setTargetRotation(this.windowManager.defaultDisplay.rotation)
            .build()
        preview.setSurfaceProvider(camera.createSurfaceProvider())
        try {
            cameraProvider.unbindAll()
            val camera: Camera = cameraProvider.bindToLifecycle(
                (this as LifecycleOwner),
                cameraSelector,
                preview,
                imageAnalysis,
                imageCapture
            )
        }catch (e:Exception){

        }

        capture.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val mDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
                val file =
                    File(this@CameraActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), mDateFormat.format(Date()).toString() + ".jpg")
                val outputFileOptions: OutputFileOptions = OutputFileOptions.Builder(file).build()
                imageCapture.takePicture(
                    outputFileOptions,
                    executor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(@NonNull outputFileResults: OutputFileResults) {

                            Intent().apply {
                                this.putExtra("uri",outputFileResults.savedUri)
                                setResult(Activity.RESULT_OK,this)
                            }
                            finish()

                        }

                        override fun onError(@NonNull error: ImageCaptureException) {
                            error.printStackTrace()
                        }
                    })
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.frontFaceCamera->{
                if (cameraLanceSelector==CameraSelector.LENS_FACING_BACK){
                    cameraLanceSelector=CameraSelector.LENS_FACING_FRONT
                    startCamera()
                }else{
                    cameraLanceSelector=CameraSelector.LENS_FACING_BACK
                    startCamera()
                }
            }
        }
    }


}