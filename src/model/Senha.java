package model;

public class Senha {

    public void confirmaSenha(String senha){
       if(senha.length() < 8){
           throw new IllegalArgumentException("ERRO! tamanho incorreto no mínimo 8 caracteres.");
       }

       if(!validarSeguranca(senha)){
           throw new IllegalArgumentException("ERRO! senha deve ter no mínimo um simbolo e uma maiúscula.");
       }
    }

    public boolean validarSeguranca(String senha){
        boolean temSimbolo = senha.matches(".*[^a-zA-Z0-9].*");
        boolean temMaiuscula = senha.matches(".*[A-Z].*");

        return temSimbolo && temMaiuscula;
    }

    public boolean validarSenha(Pessoa pessoa, String senhaDigitada){
        return senhaDigitada.equals(pessoa.getSenha());
    }
}
