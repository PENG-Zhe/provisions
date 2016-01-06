/*
 * Decompiled with CFR 0_110.
 */
package edu.stanford.crypto.proof.assets;

import edu.stanford.crypto.ECConstants;
import edu.stanford.crypto.proof.MemoryProof;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class AddressProof
implements MemoryProof {
    private final ECPoint commitmentBalance;
    private final ECPoint commitmentXHat;
    private final BigInteger challenge;
    private final BigInteger responseS;
    private final BigInteger responseV;
    private final BigInteger responseT;
    private final BigInteger responseXHat;

    public AddressProof(ECPoint commitmentBalance, ECPoint commitmentXHat, BigInteger challenge, BigInteger responseS, BigInteger responseV, BigInteger responseT, BigInteger responseXHat) {
        this.commitmentBalance = commitmentBalance;
        this.commitmentXHat = commitmentXHat;
        this.challenge = challenge;
        this.responseS = responseS;
        this.responseV = responseV;
        this.responseT = responseT;
        this.responseXHat = responseXHat;
    }

    public AddressProof(byte[] array) {
        int index = 0;
        byte statementLength = array[index++];
        this.commitmentBalance = ECConstants.BITCOIN_CURVE.decodePoint(Arrays.copyOfRange(array, index, statementLength + index));
        index += statementLength;
        statementLength = array[index++];
        this.commitmentXHat = ECConstants.BITCOIN_CURVE.decodePoint(Arrays.copyOfRange(array, index, statementLength + index));
        index += statementLength;
        statementLength = array[index++];
        this.challenge = new BigInteger(Arrays.copyOfRange(array, index, statementLength + index));
        index += statementLength;
        statementLength = array[index++];
        this.responseS = new BigInteger(Arrays.copyOfRange(array, index, statementLength + index));
        index += statementLength;
        statementLength = array[index++];
        this.responseT = new BigInteger(Arrays.copyOfRange(array, index, statementLength + index));
        index += statementLength;
        statementLength = array[index++];
        this.responseV = new BigInteger(Arrays.copyOfRange(array, index, statementLength + index));
        index += statementLength;
        statementLength = array[index++];
        this.responseXHat = new BigInteger(Arrays.copyOfRange(array, index, statementLength + index));
    }

    @Override
    public byte[] serialize() {
        byte[] commitmentBalanceEncoded = this.commitmentBalance.getEncoded(true);
        byte[] commitmentXHatEncoded = this.commitmentXHat.getEncoded(true);
        byte[] challengeEncoded = this.challenge.toByteArray();
        byte[] responseSEncoded = this.responseS.toByteArray();
        byte[] responseTEncoded = this.responseT.toByteArray();
        byte[] responseVEncoded = this.responseV.toByteArray();
        byte[] responseXHatEncoded = this.responseXHat.toByteArray();
        List<byte[]> arrList = Arrays.asList(commitmentBalanceEncoded, commitmentXHatEncoded, challengeEncoded, responseSEncoded, responseTEncoded, responseVEncoded, responseXHatEncoded);
        int totalLength = arrList.stream().mapToInt(arr -> arr.length).map(i -> i + 1).sum();
        byte[] fullArray = new byte[totalLength];
        int currIndex = 0;
        for (byte[] arr2 : arrList) {
            fullArray[currIndex++] = (byte)arr2.length;
            System.arraycopy(arr2, 0, fullArray, currIndex, arr2.length);
            currIndex += arr2.length;
        }
        return fullArray;
    }

    public BigInteger getChallenge() {
        return this.challenge;
    }

    public BigInteger getResponseS() {
        return this.responseS;
    }

    public ECPoint getCommitmentBalance() {
        return this.commitmentBalance;
    }

    public ECPoint getCommitmentXHat() {
        return this.commitmentXHat;
    }

    public BigInteger getResponseV() {
        return this.responseV;
    }

    public BigInteger getResponseT() {
        return this.responseT;
    }

    public BigInteger getResponseXHat() {
        return this.responseXHat;
    }
}

