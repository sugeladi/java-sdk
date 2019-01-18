package crypto;

import model.transaction.Signature;

import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class Ed25519 extends KeyPair {
    private byte[] privateKey;
    private byte[] publicKey;

    public Ed25519() {
        publicKey = new byte[TweetNaCl.SIGN_PUBLIC_KEY_BYTES];
        privateKey = new byte[TweetNaCl.SIGN_SECRET_KEY_BYTES];
        TweetNaCl.crypto_sign_keypair(publicKey, privateKey, false);
    }

    public Ed25519(byte[] seckey){
        this.privateKey = seckey;
        this.publicKey = Arrays.copyOfRange(seckey, seckey.length/2, seckey.length);
    }

    @Override
    public Signature sign(byte[] info) {
        byte[]sig = TweetNaCl.crypto_sign(info, this.privateKey);

        Signature signature = new Signature();
        signature.signature = sig;
        signature.algorithm = Algorithm.Ed25519;
        signature.public_key = this.pubkey();

        return signature;
    }

    @Override
    public byte[] pubkey() {
        return this.publicKey;
    }

    @Override
    public byte[] seckey() {
        return this.privateKey;
    }
}