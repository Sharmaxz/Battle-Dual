package com.sharmaxz.battle_dual.service

import com.sharmaxz.battle_dual.model.User
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class SignUpServiceTest {

    @Test
    fun `Check if user is unique`() {
        val result = SignUpService.post("testunitario2", "testunitario","testunitario2",
            "testunitario@test2.com","testunitario","2020-03-29")
        val expected = mutableMapOf("response" to 400, "result" to "{\"nickname\":[\"Usuário with this nickname already exists.\"],\"email\":[\"Usuário with this email already exists.\"]}")
        assertEquals(expected , result)
    }

    //Rodar esse teste offline ou ele criará o usuario
    @Test
    fun `Check the connection`() {
        val result = SignUpService.post("testoffline", "testoffline","testoffline",
            "testoffline@test2.com","testoffline","2020-03-29")
        val expected = mutableMapOf("response" to 0)

        assertEquals(expected, result)
    }

    // Gera numeros randomicos para gerar novos usuarios sem conflito de email e nick.
    // Pega a response gerada e tranfroma em User já que a senha não é transitada por segurança.
    // Se a response retornar um User com o mesmo nickname(UniqueKEY) do enviado o usuario foi criado com sucesso!!
    @Test
    fun `Check registration`() {
        var rdn = Random.nextInt(0, 100)
        var rdn2 = Random.nextInt(100, 200)
        var generator = listOf("nomeTeste", "sobrenomeTest", "${rdn}nickname${rdn2}", "furry${rdn}@Gordin${rdn2}.com", "password", "2020-03-29")
        val signUP =  SignUpService.post(generator[0], generator[1], generator[2], generator[3], generator[4], generator[5])
        val userReturned = signUP as User
        val expected = generator[2]
        assertEquals(expected, userReturned.nickname)
    }

}
