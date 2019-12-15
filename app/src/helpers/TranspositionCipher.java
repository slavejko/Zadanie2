package helpers;

public class TranspositionCipher {


    public String encryptRows(String pt, TranspositionKey k) {

        // doplnime aby sme mali vstup velkosti nasobku kluca
        StringBuilder pt2 = new StringBuilder(pt);
        int missing = 0;

        while((pt2.length()%k.blockSize)!=0){
            pt2.append('x');
            missing++;
        }
        //System.out.println("Dlzka OT: "+pt.length());
        //System.out.println("Dlzka kluca: "+k.blockSize);
        //System.out.println("Pocet doplnenych znakov:" + missing);

        // sifrovanie
        StringBuilder ct = new StringBuilder();
        for (int i = 0; i < pt2.length(); i += k.blockSize) {
            String block = pt2.substring(i, i + k.blockSize);
            block = applyPermutation(block, k.encPerm);
            ct.append(block);
        }

        return ct.toString();
    }

    public String encyptCols(String pt, TranspositionKey k){
        String cipherText = encryptRows(pt,k);
        return readColumns(cipherText, k.blockSize);
    }

    public String decryptRows(String ct, TranspositionKey k) {

        // desifrovanie
        int missing=0;
        StringBuilder ct2 = new StringBuilder(ct);
        while((ct2.length()%k.blockSize)!=0){
            ct2.append('x');
            missing++;
        }

        StringBuilder pt = new StringBuilder();
        for (int i = 0; i < ct2.length(); i += k.blockSize) {
            String block = ct2.substring(i, i + k.blockSize);
            block = applyPermutation(block, k.decPerm);
            pt.append(block);
        }

        return pt.toString();
    }

    public String decryptCols(String ct, TranspositionKey k){
        String cipherTextRows = readRows(ct, k.blockSize);
        return decryptRows(cipherTextRows,k);
    }
    /*
    perm z M={1,..,n}
    */
    private String applyPermutation(String input, Integer[] perm) {
        char output[] = new char[perm.length];
        for (int i=0; i < perm.length; i++) {
            output[perm[i]-1] = input.charAt(i);
        }
        return new String(output);
    }
    /*
    blockSize = columns
    */

    /*
    * Inputs:   text : transform input text written in rows to cols
    *           blockSize : size of block - number of cols
    */
    public String readColumns(String text, int blockSize){
        StringBuilder sb = new StringBuilder(text);
        while((sb.length()%blockSize)!=0){
            sb.append('x');
        }
        StringBuilder retString= new StringBuilder();
        for(int i = 0; i < blockSize; i++){
            for(int j = i; j < sb.length() ; j+=blockSize){
                retString.append(sb.charAt(j));
            }
        }
        return retString.toString();
    }
    /*
     * Inputs : text : transform input text written in rows to cols
     *          blocksize: size of block - number of cols
     */
    public String readRows(String text, int blockSize){
        StringBuilder sb = new StringBuilder(text);
        while((sb.length()%blockSize)!=0){
            sb.append('x');
        }
        int rows = sb.length() / blockSize;
        StringBuilder retString = new StringBuilder();
        for(int i = 0 ; i < rows;i++){
            for(int j = i ; j < sb.length() ; j+=rows){
                retString.append(sb.charAt(j));
            }
        }
        return retString.toString();
    }
}