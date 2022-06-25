package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.Role
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserModel(
    @Column(name = "id_user", unique = true, updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idUser: Long? = null,

    @Column(name = "user_key",nullable = false, unique = true)
    val userKey: String = UUID.randomUUID().toString(),

    @Column(name = "user_name", nullable = false, unique = true)
    val userName: String,

    @Column(nullable = false, unique = true)
    val password: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(name = "verified_email", nullable = false)
    val verifiedEmail: Boolean = false,

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Column(name = "verified_phone", nullable = false)
    val verifiedPhone: Boolean = false,

    @OneToOne
    @JoinColumn(name = "person_id")
    val person: PersonModel,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    var roles: Set<Role> = setOf()

)
