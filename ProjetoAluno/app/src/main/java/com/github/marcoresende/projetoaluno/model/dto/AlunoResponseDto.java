package com.github.marcoresende.projetoaluno.model.dto;

import com.github.marcoresende.projetoaluno.model.Aluno;

import java.util.List;

/**
 * Created by MarcoResende on 24/09/2016.
 */
public class AlunoResponseDto {
    private List<Aluno> results;

    public List<Aluno> getResults() {
        return results;
    }

    public void setResults(List<Aluno> results) {
        this.results = results;
    }
}
