//package io.github.mmpodkanski.movieroom.movie;
//
//import io.github.mmpodkanski.movieroom.actor.ActorSimpleDto;
//import io.github.mmpodkanski.movieroom.movie.Comment;
//import io.github.mmpodkanski.movieroom.movie.dto.CommentResponseDto;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class MovieResponseDtoCopy {
//    private final int id;
//    private final String title;
//    private final String description;
//    private final String director;
//    private final String producer;
//    private final String category;
//    private final String releaseDate;
//    private final List<ActorSimpleDto> actors;
//    private final List<Comment> comments;
//    private final boolean acceptedByAdmin;
//    private final String imgLogoUrl;
//    private final String imgBackUrl;
//
//    public MovieResponseDtoCopy(Movie source) {
//        id = source.getId();
//        title = source.getTitle();
//        description = source.getDescription();
//        director = source.getDirector();
//        producer = source.getProducer();
//        category = source.getCategory().name();
//        releaseDate = source.getReleaseDate();
//        actors = source.getActors()
//                .stream()
//                .map(actor -> new ActorSimpleDto(actor.getId(), actor.getFirstName(), actor.getLastName()))
//                .sorted(Comparator.comparing(ActorSimpleDto::getFirstName))
//                .collect(Collectors.toList());
//
//        comments = source.getComments()
//                .stream()
//                .map(comment -> new CommentResponseDto(comment))
//                .sorted(Comparator.comparing(Comment::getCreatedAt))
//                .collect(Collectors.toList());
//
//        acceptedByAdmin = source.isAcceptedByAdmin();
//        imgLogoUrl = source.getImgLogoUrl();
//        imgBackUrl = source.getImgBackUrl();
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getDirector() {
//        return director;
//    }
//
//    public String getProducer() {
//        return producer;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public String getReleaseDate() {
//        return releaseDate;
//    }
//
//    public List<ActorSimpleDto> getActors() {
//        return actors;
//    }
//
//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public boolean isAcceptedByAdmin() {
//        return acceptedByAdmin;
//    }
//
//    public String getImgLogoUrl() {
//        return imgLogoUrl;
//    }
//
//    public String getImgBackUrl() {
//        return imgBackUrl;
//    }
//
//}
//
