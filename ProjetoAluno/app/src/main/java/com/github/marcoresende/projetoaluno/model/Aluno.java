package com.github.marcoresende.projetoaluno.model;

/**
 * Created by MarcoResende on 18/09/2016.
 */
public class Aluno {

    private String objectId;
    private String endereco;
    private String fotoUrl;
    private String telefone;
    private String nome;
    private Integer idade;
    private String createdAt;
    private String updatedAt;

    public Aluno(){}

    public Aluno(String objectId, String nome, String endereco, String fotoUrl, Integer idade){
        this.objectId = objectId;
        this.nome = nome;
        this.endereco = endereco;
        this.fotoUrl = fotoUrl;
        this.idade = idade;
    }

    /**
     *
     * Métodos de conveniencia
     */

    public boolean isValido(){
        if(this.getNome() == null || this.getNome().isEmpty()
                || this.getEndereco() == null || this.getEndereco().isEmpty()
                || this.getFotoUrl() == null || this.getFotoUrl().isEmpty()
                || idade == null || idade < 1){
            return false;
        }

        return true;
    }

    /*
        Getters & setters
     */
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
