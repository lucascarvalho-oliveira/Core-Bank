package model;

public class Senha {

    public void confirmaSenha(String senha){
       if(senha.length() < 8){
           throw new IllegalArgumentException("ERRO! tamanho incorreto no mínimo 8 caracteres.");
       }

       char primeiraLetra = senha.charAt(0);
       if(!Character.isUpperCase(primeiraLetra)){
           throw new IllegalArgumentException("ERRO! Primeira letra precisa ser maiúscula");
       }

       if(!validarSimbolos(senha)){
           throw new IllegalArgumentException("ERRO! senha deve ter no mínimo um simbolo");
       }
    }

    public boolean validarSimbolos(String senha){
        String simbolos = ".*[^a-zA-Z0-9].*";

        return senha.matches(simbolos) && senha.matches(".*/[A-Z]/.*");
    }

    public boolean validarSenha(Pessoa pessoa, String senhaDigitada){
        return pessoa.getSenha().equals(senhaDigitada);
    }
}
