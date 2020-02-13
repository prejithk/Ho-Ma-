/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credentials;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

/**
 *
 * @author HP
 */
public class KeyGeneration {

   public BigInteger privKey;
   public BigInteger pubKey;
    ECKeyPair keyPair;

    public KeyGeneration() {
        try {
            privKey = Keys.createEcKeyPair().getPrivateKey();
            pubKey = Sign.publicKeyFromPrivate(privKey);
          
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(KeyGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BigInteger getPubKey() {
        return pubKey;
    }

    public ECKeyPair getKeyPair() {
        return keyPair;
    }

}
