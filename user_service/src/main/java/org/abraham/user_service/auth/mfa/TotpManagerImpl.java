package org.abraham.user_service.auth.mfa;


import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import static dev.samstevens.totp.util.Utils.getDataUriForImage;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TotpManagerImpl implements TotpManager {

    @Autowired
    private SecretGenerator secretGenerator;


    @Autowired
    private QrGenerator qrGenerator;

    @Autowired
    private CodeVerifier verifier;

    @Override
    public String generateSecret() {
        return secretGenerator.generate();
    }

    @Override
    public String generateCode(String secret, String email) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .label(email)
                .secret(secret)
                .issuer("E_COMMERCE")
                .algorithm(HashingAlgorithm.SHA512)
                .digits(6)
                .period(30)
                .build();

        var imageData = qrGenerator.generate(data);
        var mimeType = qrGenerator.getImageMimeType();
        return getDataUriForImage(imageData, mimeType);
    }

    @Override
    public Boolean verifyCode(String code, String secret) {
            return verifier.isValidCode(code, secret);
    }
}
