package kr.co.kkm.cameraandgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    var photoUri:Uri? = null

    lateinit var cameraPermission: ActivityResultLauncher<Array<String>>
    lateinit var storagePermission: ActivityResultLauncher<Array<String>>

    lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storagePermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setViews()
            } else {
                Toast.makeText(baseContext, "외부 저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                binding.imagePreview.setImageURI(photoUri)
            }
        }

        storagePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun setVies() {
        binding.buttonCamera.setOnClickListener{
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    fun openCamera() {
        val photoFile = File.createTempFile("IMG_",".jpg",getExternalFilesDir(Environment.DIRECTORY_PICTURES))

        photoUri = FileProvider.getUriForFile(this, "${packageName}.provider",photoFile)

        cameraLauncher.launch(photoUri)
    }

}