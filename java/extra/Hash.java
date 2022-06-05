package extra;

public class Hash {
    private static Hash hash;

    public static Hash getInstance(){
        if (hash == null)
            hash = new Hash();
        return hash;
    }

    public String hash(String text){
        String salt = "BenazamSeyyed";
        text += salt;
        StringBuilder hasher = new StringBuilder();
        int i = 1;
        for (int j = 0; j < text.length(); j++) {
            hasher.append((text.charAt(j)-'0')*i);
            i++;
        }
        return hasher.toString();
    }
}
