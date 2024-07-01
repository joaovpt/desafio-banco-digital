import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Banco {
    private String nome;
    private List<Cliente> clientesBanco;

    public Banco(String nome) {
        this.nome = nome;
        this.clientesBanco = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cliente> getClientesBanco() {
        return clientesBanco;
    }

    public void adicionarCliente(Cliente cliente) {
        this.clientesBanco.add(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientesBanco) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public void ordenarClientesPorNome() {
        Collections.sort(clientesBanco, (Cliente c1, Cliente c2) -> c1.getNome().compareTo(c2.getNome()));
    }

}
