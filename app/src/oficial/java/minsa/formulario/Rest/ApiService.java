package minsa.formulario.Rest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("usuario/saveFile")
    @Multipart
    Call<EnvioResponse> sendFile(@Part("dni") RequestBody dni, @Part MultipartBody.Part imageFile
    );

    @GET("public/uploads/{folder}/{file}")
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Path("folder") String folder, @Path("file") String file);

}