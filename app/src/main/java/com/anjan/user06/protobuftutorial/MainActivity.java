package com.anjan.user06.protobuftutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("+49123456")
                .setType(AddressBookProtos.Person.PhoneType.HOME)
                .build();

        Album album = new Album.Builder("Songs from the Big Chair", 1985)
                .artist("Tears For Fears")
                .songTitle("Shout")
                .songTitle("The Working Hour")
                .songTitle("Everybody Wants to Rule the World")
                .songTitle("Mothers Talk")
                .songTitle("I Believe")
                .songTitle("Broken")
                .songTitle("Head Over Heels")
                .songTitle("Listen")
                .build();

        AlbumProtos.Album albumMessage
                = AlbumProtos.Album.newBuilder()
                .setTitle(album.getTitle())
                .addAllArtist(album.getArtists())
                .setReleaseYear(album.getReleaseYear())
                .addAllSongTitle(album.getSongsTitles())
                .build();

        //Writing Binary Form of AlbumProtos.Album
        byte[] binaryAlbum = albumMessage.toByteArray();

        //Reading a byte[] array back into an instance of Album
        Album albumFromBinary = instantiateAlbumFromBinary(binaryAlbum);


    }

    public Album instantiateAlbumFromBinary(final byte[] binaryAlbum) {
        Album album = null;
        try {
            final AlbumProtos.Album copiedAlbumProtos = AlbumProtos.Album.parseFrom(binaryAlbum);
            final List<String> copiedArtists = copiedAlbumProtos.getArtistList();
            final List <String> copiedSongsTitles = copiedAlbumProtos.getSongTitleList();
            album = new Album.Builder(
                    copiedAlbumProtos.getTitle(), copiedAlbumProtos.getReleaseYear())
                    .artists(copiedArtists)
                    .songsTitles(copiedSongsTitles)
                    .build();
        } catch (InvalidProtocolBufferException ipbe) {
            Log.d("TAG","ERROR: Unable to instantiate AlbumProtos.Album instance from provided binary data - " +
                    ipbe);
        }
        return album;
    }
}
