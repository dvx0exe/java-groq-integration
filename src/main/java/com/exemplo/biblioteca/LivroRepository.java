package com.exemplo.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LivroRepository {

    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
    private static final String USER = "root";

    public void salvarLivros(List<GroqClient.Livro> livros) {
        String sql = "INSERT INTO livros (nome_do_livro, ano_publicacao, editora_classica, " +
                "numero_paginas_estimado, genero_literario, resumo_sinopse) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (GroqClient.Livro livro : livros) {
                pstmt.setString(1, livro.getNomeDoLivro());
                pstmt.setString(2, livro.getAnoPublicacao());
                pstmt.setString(3, livro.getEditoraClassica());
                pstmt.setInt(4, livro.getNumeroPaginasEstimado());
                pstmt.setString(5, livro.getGeneroLiterario());
                pstmt.setString(6, livro.getResumoSinopse());

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Todos os livros foram salvos no MySQL com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }
}
