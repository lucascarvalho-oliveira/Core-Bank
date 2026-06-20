package service;

import model.Pix;
import model.enums.TipoPix;
import repository.PixRepository;

public class PixService {
    private PixRepository repositoryPix;

    public PixService(PixRepository repositoryPix){
        this.repositoryPix = repositoryPix;
    }

    public void savePix(Pix pix){
        if(pix.getChave() == null || pix.getChave().isBlank()){
            throw new IllegalArgumentException("Chave do pix incorreta!");
        }

        repositoryPix.salvarPix(pix);
    }

    public void updateChave(String chavePix, TipoPix tipoPix, String novaChave){
        if(novaChave == null || novaChave.isBlank()){
            throw new IllegalArgumentException("Chave do pix incorreta!");
        }

        repositoryPix.updateChave(chavePix, tipoPix, novaChave);
    }

    public void apagarPix(String chavePix){
        if(chavePix == null || chavePix.isBlank()){
            throw new IllegalArgumentException("Chave do pix incorreta!");
        }

        repositoryPix.apagarPix(chavePix);
    }
}
