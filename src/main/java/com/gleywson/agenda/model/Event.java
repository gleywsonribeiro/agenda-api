package com.gleywson.agenda.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private int duration; // em minutos

    @Enumerated(EnumType.STRING)
    private EventType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Event() {
    }

    public Event(Long id, String title, String description, LocalDateTime dateTime, int duration, EventType type,
                 User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.duration = duration;
        this.type = type;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
