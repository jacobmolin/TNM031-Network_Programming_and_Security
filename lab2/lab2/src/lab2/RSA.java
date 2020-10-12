package lab2;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Random;

public class RSA {

	private BigInteger p, q, n, phi, e, d;
	
	public RSA() {
		initialize();
	}
	
	// (e,n) is the public key
	public void initialize() {
		int SIZE = 512;
		
		
		// 1. Select two large, secret prime numbers, p and q
		p = BigInteger.probablePrime(SIZE, new Random());
		q = BigInteger.probablePrime(SIZE, new Random());
		// System.out.println("p = " + p);
		// System.out.println("q = " + q);
		
		// 2. Calculate n = p * q
		// To test with: n = new BigInteger("829874155521400737369906984541618575194339167227519830444619550372200486675270437049185449");
		n = p.multiply(q); 
		// System.out.println("n = " + n);
		
		// 3. Calculate phi = (p-1)(q-1)
		phi = p.subtract(BigInteger.ONE);
		phi = phi.multiply(q.subtract(BigInteger.ONE));
		
		// 4. Calculate e s.t. GCD(e, (p-1)(q-1)) = 1	
		// To test with: e = new BigInteger("817082287544309296963434846430676338067347639");
		e = BigInteger.probablePrime(phi.bitLength(), new Random());		
		
		
		while (e.compareTo(phi) != 1 || (e.gcd(phi).compareTo(BigInteger.ONE) != 0)) {
			e = new BigInteger(phi.bitLength(), new Random());
			//System.out.println("e = " + e);
		}
		System.out.println("The public key (e,n) have been calculated.\n");
		
        // 5. Calculate d s.t. e*d mod((p-1)(q-1)) = 1 
		d = e.modInverse(phi);
		
	}
	
	public BigInteger encrypt(BigInteger text) {
		return text.modPow(e, n);
	}
	
	public BigInteger decrypt(BigInteger ciphertext) {
		return ciphertext.modPow(d, n);
	}

	
	public static void main(String[] args) throws IOException {
		RSA myRSA = new RSA();
		
		System.out.println("Enter a message to be encrypted: ");
		String input_text = (new BufferedReader(new InputStreamReader(System.in))).readLine();
		
		// To test with: BigInteger ciphertext = new BigInteger("487503907191231875128908770905681176229204726561587507748049184573487425647683465148993114");
		BigInteger ciphertext = myRSA.encrypt(new BigInteger(input_text.getBytes()));
		System.out.println("\nEncrypted text: " + ciphertext);
		
		String text_decrypted = new String(myRSA.decrypt(ciphertext).toByteArray());
		System.out.println("Decrypted text: " + text_decrypted);
	}
}
