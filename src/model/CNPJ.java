package model;

public class CNPJ {
    private final String cnpj;

    public CNPJ(String cnpj){
        if(cnpj == null || !ehValido(cnpj.replaceAll("\\D", ""))){
            throw new IllegalArgumentException("CNPJ inválido");
        }

        this.cnpj = cnpj.replaceAll("\\D", "");
    }

    public boolean ehValido(String cnpj){
        return cnpj.length() == 14 && validarRepeticao(cnpj) && digitos(cnpj);
    }

    public boolean validarRepeticao(String cnpj){
        boolean ehIguais = true;

        for(int i = 1; i < cnpj.length(); i++){
            if(cnpj.charAt(0) != cnpj.charAt(i)){
                ehIguais = false;
                break;
            }
        }
        return !ehIguais;
    }

    public boolean digitos(String cnpj){
        int[] peso_1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso_2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int primeiroDigito = validarDigito(cnpj.substring(0, 12), peso_1);
        int segundoDigito = validarDigito(cnpj.substring(0, 13), peso_2);

        return primeiroDigito == cnpj.charAt(12) - '0' && segundoDigito == cnpj.charAt(13) - '0';
    }

    public int validarDigito(String cnpj, int[] peso){
        int soma = 0;

        for(int i = 0; i < cnpj.length(); i++){
            int numero = cnpj.charAt(i) - '0';
            soma += numero * peso[i];
        }

        int restoDivisao = soma % 11;

        if(restoDivisao < 2){
            return 0;
        }else{
            return 11 - restoDivisao;
        }
    }

    @Override
    public String toString() {
        return this.cnpj;
    }
}
