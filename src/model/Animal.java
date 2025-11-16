package model;

public class Animal {
    private int id;
    private String nome;
    private String categoria;
    private String especie;
    private int idade;
    private String habitat;
    private String alimentacao;
    private String descricao;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getCategoria(){
        return this.categoria;
    }

    public void setEspecie(String especie){
        this.especie = especie;
    }

    public String getEspecie(){
        return this.especie;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public int getIdade(){
        return this.idade;
    }

    public void setHabitat(String habitat){
        this.habitat = habitat;
    }

    public String getHabitat(){
        return this.habitat;
    }

    public void setAlimentacao(String alimentacao){
        this.alimentacao = alimentacao;
    }

    public String getAlimentacao(){
        return this.alimentacao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}
