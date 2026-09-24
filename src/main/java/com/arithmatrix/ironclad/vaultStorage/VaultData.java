package com.arithmatrix.ironclad.vaultStorage;

public class VaultData {
    private int version;
    private String salt;
    private String iv;
    private String data;

    public VaultData(){}

    public static VaultData createVaultData(int version, String salt, String iv, String data){
        VaultData vd = new VaultData();
        vd.version = version;
        vd.salt = salt;
        vd.iv = iv;
        vd.data = data;
        return vd;
    }

    public int getVersion() {
        return version;
    }

    public String getSalt() {
        return salt;
    }

    public String getIv() {
        return iv;
    }

    public String getData() {
        return data;
    }
}
