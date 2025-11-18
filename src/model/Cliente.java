package model;

import dao.*;
import view.*;

public class Cliente extends Usuario{

    public Cliente(int id, String nome, String email, String senha){
        // Passa os valores para a classe mãe, definindo o tipo como "Cliente"
        super(id, nome, email, senha, "Cliente");
    }

    @Override
    public void mostrarMenuPrincipal(Menu menu) {
        menu.menuCliente(this);
    }
}
