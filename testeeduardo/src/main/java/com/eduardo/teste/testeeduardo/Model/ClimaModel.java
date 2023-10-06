package com.eduardo.teste.testeeduardo.Model;


import jakarta.validation.constraints.NotBlank;

public class ClimaModel {

    @NotBlank(message = "O campo CNPJ não pode estar em branco.")
    private String cnpjInfo;

    public String getCnpjInfo() {
        return cnpjInfo;
    }

    public void setCnpjInfo(String cnpjInfo) {
        this.cnpjInfo = cnpjInfo;
    }
}
