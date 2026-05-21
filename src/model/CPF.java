package model;

public class CPF {
    private final String cpf;

    public CPF(String cpf){
        if(cpf == null || !ehValido(cpf.replaceAll("\\D", ""))){
            throw new IllegalArgumentException("CPF inválido");
        }

        this.cpf =  cpf.replaceAll("\\D", "");
    }

    public boolean ehValido(String cpf){
        return cpf.length() == 11 && validarRepeticao(cpf) && digitos(cpf);
    }

    public boolean validarRepeticao(String cpf){
        boolean heIguais = true;
        for(int i = 1; i < cpf.length(); i++){
            if(cpf.charAt(0) != cpf.charAt(i)){
                heIguais = false;
            }
        }
        return !heIguais;
    }

    public boolean digitos(String cpf){
        int primeiroDigito = validarDigitos(cpf.substring(0, 9), 10);
        int segundoDigito = validarDigitos(cpf.substring(0, 10), 11);

        return primeiroDigito == cpf.charAt(9) - '0' && segundoDigito == cpf.charAt(10) - '0';
    }

    public int validarDigitos(String cpf, int peso){
        int soma = 0;

        for(int i = 0; i < cpf.length(); i++){
            int numero = cpf.charAt(i) - '0';
            soma += numero * peso;
            peso--;
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
        return this.cpf;
    }
}
