package net.theykk.ubucum

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.net.URL
import java.util.concurrent.Executor


class MainActivity: AppCompatActivity() {
    private lateinit
    var executor: Executor
    private lateinit
    var biometricPrompt: BiometricPrompt
    private lateinit
    var promptInfo: BiometricPrompt.PromptInfo
    private  var action = 1;
    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object: BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                if (action == 1 ){
                    var res = URL("http://kaan-ubu:8080/fe910f0f-0ed3-4ad7-ac71-944c96aba71c").readText();
                    Toast.makeText(applicationContext, res, Toast.LENGTH_SHORT).show()
                }else{
                    var res = URL("http://kaan-ubu:8080/0b63f3a3-2d28-423e-90f4-da7af27b83f5").readText();
                    Toast.makeText(applicationContext, res, Toast.LENGTH_SHORT).show()
                }



            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login for UBUCUM").setSubtitle("Log in using your biometric credential").setNegativeButtonText("Use account password").build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        val biometricLoginButton = findViewById < Button > (R.id.unlock)
        biometricLoginButton.setOnClickListener {
            action = 1;
            biometricPrompt.authenticate(promptInfo)
        }
        val lockbtn = findViewById < Button > (R.id.lock)
        lockbtn.setOnClickListener {
            action = 2;
            biometricPrompt.authenticate(promptInfo)
        }
//        biometricPrompt.authenticate(promptInfo)
    }
}