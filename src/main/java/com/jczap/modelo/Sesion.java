package com.jczap.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sesiones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sesion {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "usuario_id", nullable = false)
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
   private Usuario usuario;
   
   @Column(nullable = false, unique = true)
   private String token;
   
   @Column(name = "fecha_inicio")
   private LocalDateTime fechaInicio;
   
   @Column(name = "fecha_expiracion", nullable = false)
   private LocalDateTime fechaExpiracion;
   
   private Boolean activa = true;
   
   @Column(name = "ip_address", length = 45)
   private String ipAddress;
   
   @Column(name = "user_agent", columnDefinition = "TEXT")
   private String userAgent;
   
   @Column(name = "fecha_fin")
   private LocalDateTime fechaFin;
   
   // Constructores
   public Sesion() {
       this.fechaInicio = LocalDateTime.now();
   }
   
   public Sesion(Usuario usuario, String token, LocalDateTime fechaExpiracion) {
       this();
       this.usuario = usuario;
       this.token = token;
       this.fechaExpiracion = fechaExpiracion;
   }
   
   // Getters y Setters
   public Integer getId() { return id; }
   public void setId(Integer id) { this.id = id; }
   
   public Usuario getUsuario() { return usuario; }
   public void setUsuario(Usuario usuario) { this.usuario = usuario; }
   
   public String getToken() { return token; }
   public void setToken(String token) { this.token = token; }
   
   public LocalDateTime getFechaInicio() { return fechaInicio; }
   public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
   
   public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
   public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
   
   public Boolean getActiva() { return activa; }
   public void setActiva(Boolean activa) { this.activa = activa; }
   
   public String getIpAddress() { return ipAddress; }
   public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
   
   public String getUserAgent() { return userAgent; }
   public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
   
   public LocalDateTime getFechaFin() {
       return fechaFin;
   }

   public void setFechaFin(LocalDateTime fechaFin) {
       this.fechaFin = fechaFin;
   }
   
   // Métodos de utilidad
   public boolean isExpired() {
       return LocalDateTime.now().isAfter(this.fechaExpiracion);
   }
   
   public boolean isValid() {
       return this.activa && !isExpired();
   }
}