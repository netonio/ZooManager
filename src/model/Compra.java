package model;

public class Compra {
    private int id;
    private int id_usuario;
    private int id_ingresso;
    private int quantidade;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setId_usuario(int id_usuario){
        this.id_usuario = id_usuario;
    }

    public int getId_usuario(){
        return this.id_usuario;
    }

    public void setId_ingresso(int id_ingresso){
        this.id_ingresso = id_ingresso;
    }

    public int getId_ingresso(){
        return this.id_ingresso;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public int getQuantidade(){
        return this.quantidade;
    }
}
