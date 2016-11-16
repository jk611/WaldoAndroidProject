package me.kingjason.waldoproject;

import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestJSONParsing
{
    @Test
    public void test_empty_string() throws Exception
    {
        assertNull( Utils.parseJson("") );
    }

    @Test
    public void test_null() throws Exception
    {
        assertNull( Utils.parseJson(null) );
    }

    @Test
    public void test_invalid_json() throws Exception
    {
        assertNull( Utils.parseJson("not json") );
    }

    @Test
    public void test_valid_json() throws Exception
    {
        assertNotNull( Utils.parseJson("{\"name\":\"Jason\"}") );
    }

    @Test
    public void test_readAlbumList() throws Exception
    {
        List<Photo> list = Utils.readAlbumList( new JSONObject( "{\n" +
                "  \"data\": {\n" +
                "    \"album\": {\n" +
                "      \"id\": \"YWxidW06ODRkMjg4YzEtNGFiYi00N2IwLWFmYmUtNjM3ODkxYzA0ZWU5\",\n" +
                "      \"name\": \"tbarm57270\",\n" +
                "      \"photos\": {\n" +
                "        \"records\": [\n" +
                "          {\n" +
                "            \"id\": \"cGhvdG86ZjM0OGY1ZTgtNTg0ZC00MTZmLTg3YzEtZmVkODk1ZjI1MjFk\",\n" +
                "            \"urls\": [\n" +
                "              {\n" +
                "                \"size_code\": \"small\",\n" +
                "                \"url\": \"https://waldo-thumbs-dev.s3.amazonaws.com/small/f348f5e8-584d-416f-87c1-fed895f2521d.jpg\",\n" +
                "                \"width\": 96,\n" +
                "                \"height\": 64,\n" +
                "                \"quality\": 0.3,\n" +
                "                \"mime\": null\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}" ) );

        assertEquals( 1, list.size() );
    }

    @Test
    public void test_readAlbumList_missing_field() throws Exception
    {
        List<Photo> list = Utils.readAlbumList( new JSONObject( "{\n" +
                "  \"data\": {\n" +
                "    \"album\": {\n" +
                "      \"id\": \"YWxidW06ODRkMjg4YzEtNGFiYi00N2IwLWFmYmUtNjM3ODkxYzA0ZWU5\",\n" +
                "      \"name\": \"tbarm57270\",\n" +
                "      \"photos\": {\n" +
                "        \"records\": [\n" +
                "          {\n" +
                "            \"id\": \"cGhvdG86ZjM0OGY1ZTgtNTg0ZC00MTZmLTg3YzEtZmVkODk1ZjI1MjFk\",\n" +
                "            \"urls\": [\n" +
                "              {\n" +
                "                \"url\": \"https://waldo-thumbs-dev.s3.amazonaws.com/small/f348f5e8-584d-416f-87c1-fed895f2521d.jpg\",\n" +
                "                \"width\": 96,\n" +
                "                \"quality\": 0.3,\n" +
                "                \"mime\": null\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}" ) );

        assertEquals( list.size(), 0 );
    }

    @Test
    public void test_readAlbumList_valid_json_no_url() throws Exception
    {
        List<Photo> list = Utils.readAlbumList( new JSONObject( "{\n" +
                "  \"data\": {\n" +
                "    \"album\": {\n" +
                "      \"id\": \"YWxidW06ODRkMjg4YzEtNGFiYi00N2IwLWFmYmUtNjM3ODkxYzA0ZWU5\",\n" +
                "      \"name\": \"tbarm57270\",\n" +
                "      \"photos\": {\n" +
                "        \"records\": [\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}" ) );

        assertEquals( list.size(), 0 );
    }

    @Test
    public void test_readAlbumList_valid_json_empty() throws Exception
    {
        List<Photo> list = Utils.readAlbumList( new JSONObject( "{}" ) );

        assertEquals( list.size(), 0 );
    }
}