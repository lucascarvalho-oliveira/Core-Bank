package service;

import model.Pix;
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
}
