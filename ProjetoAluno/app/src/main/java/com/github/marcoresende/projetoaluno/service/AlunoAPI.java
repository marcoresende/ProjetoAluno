package com.github.marcoresende.projetoaluno.service;

import com.github.marcoresende.projetoaluno.model.Aluno;
import com.github.marcoresende.projetoaluno.model.dto.AlunoResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by MarcoResende on 18/09/2016.
 */
public interface AlunoAPI {

    String BASE_URL = "";

    @Headers({
            "X-Parse-Application-Id:",
            "X-Parse-REST-API-Key:"
    })
    @GET("Aluno")
    Call<AlunoResponseDto> getAlunos();

    @Headers({
            "X-Parse-Application-Id:",
            "X-Parse-REST-API-Key:"
    })
    @DELETE("Aluno/{objectId}")
    Call<Void> deleteAluno(@Path("objectId") String alunoId);

    @Headers({
            "X-Parse-Application-Id:",
            "X-Parse-REST-API-Key:"
    })
    @POST("Aluno")
    Call<Object> addAluno(@Body Aluno aluno);

}
