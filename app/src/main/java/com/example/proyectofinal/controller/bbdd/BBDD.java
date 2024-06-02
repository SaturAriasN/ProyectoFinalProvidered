package com.example.proyectofinal.controller.bbdd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.LoginActivity;
import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.controller.adapters.AdapterCheckProvider;
import com.example.proyectofinal.controller.adapters.AdapterProducts;
import com.example.proyectofinal.controller.utils.Utils;
import com.example.proyectofinal.model.Cart;
import com.example.proyectofinal.model.Category;
import com.example.proyectofinal.model.Order;
import com.example.proyectofinal.model.Product;
import com.example.proyectofinal.model.Provider;
import com.example.proyectofinal.ui.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BBDD {

    public static ArrayList<Category> categories = new ArrayList<>();
    public static ArrayList<Provider> providers = new ArrayList<>();

    private static final String SECRET_KEY = "cs_b38d3d19ce36f37634ba645e2757cdd843e4f279";
    private static final String CLIENT_KEY = "ck_f7707823d0a921b621b721585d12c69802e14979";

    private static final String AUTH = "?consumer_key=" + CLIENT_KEY + "&consumer_secret=" + SECRET_KEY;


    public static void getProducts(Context context, String url) {
        ArrayList<Product> products = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + AUTH, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        Product product = new Product();
                        JSONObject object = response.getJSONObject(i);
                        product.setName(object.getString("name"));
                        product.setPrice(object.getInt("price"));
                        product.setCategory(object.getJSONArray("categories").getJSONObject(0).getString("name"));
                        product.setDesc(object.getString("description"));
                        JSONArray a = object.getJSONArray("images");
                        if (a.length() > 0) {
                            product.setUrl(a.getJSONObject(0).getString("src"));
                        }
                        Provider provider = new Provider();
                        if (object.getJSONArray("tags").length() > 0) {
                            JSONObject t = object.getJSONArray("tags").getJSONObject(0);
                            provider.setName(t.getString("name"));
                        } else {
                            provider.setName("DEFAULT");
                        }
                        product.setProvider(provider);

                        products.add(product);

                    }

                    setCategories(products);
                    getCategories(context, url + "/categories");
                    setProviders(products);
                    getTagsDescription(context, url + "/tags");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        });
        queue.add(request);

    }

    public static void createProduct(Context context, String url, Product product, int category, String provi) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject productJson = new JSONObject();
            productJson.put("name", product.getName());
            productJson.put("regular_price", "" + product.getPrice());
            productJson.put("type", "simple");
            productJson.put("description", product.getDesc());
            productJson.put("short_description", product.getDesc());


            JSONArray categoriesArray = new JSONArray();
            JSONObject categoryJson = new JSONObject();
            categoryJson.put("id", category);
            categoriesArray.put(categoryJson);

            productJson.put("categories", categoriesArray);

            JSONArray tagsArray = new JSONArray();
            JSONObject tagJson = new JSONObject();
            tagJson.put("name", provi);
            tagsArray.put(tagJson);

            productJson.put("tags", tagsArray);

            JSONArray imagesArray = new JSONArray();
            JSONObject image = new JSONObject();
            image.put("src", product.getUrl());
            imagesArray.put(image);

            productJson.put("images", imagesArray);
            Log.d("Product JSON", productJson.toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url + AUTH, productJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", error.toString());
                }
            });

            queue.add(request);
        } catch (
                JSONException e) {
            Log.e("JSON Error", e.toString());
        }

    }


    public static void createCategory(Context context, String url, Category category) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject productJson = new JSONObject();
            productJson.put("name", category.getName());


            JSONObject image = new JSONObject();
            image.put("src", category.getUrl());

            productJson.put("image", image);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url + AUTH, productJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", error.toString());
                }
            });

            queue.add(request);
        } catch (
                JSONException e) {
            Log.e("JSON Error", e.toString());
        }

    }

    public static void getCategories(Context context, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + AUTH, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String as = object.toString();
                        Category category = new Category(object.getString("name"));
                        category.setId(object.getInt("id"));
                        JSONObject obj = object.getJSONObject("image");
                        category.setUrl(obj.getString("src"));
                        int pos = categories.indexOf(category);
                        if (pos == -1) {
                            categories.add(category);
                        } else {
                            categories.get(pos).setId(object.getInt("id"));
                            categories.get(pos).setUrl(obj.getString("src"));
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        });
        queue.add(request);
    }


    public static void getProviders(Context context, String url) {


        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + AUTH, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        Provider provider = new Provider(object.getString("name"));

                        if (!providers.contains(provider)) {
                            providers.add(provider);
                        }
                    }

                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        });
        queue.add(request);

    }


    public static void getLogin(Context context, String url, LoginActivity loginActivity) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(context, response.getInt("response") == 1 ? "CORRECTO" : "MAL", Toast.LENGTH_SHORT).show();
                    if (response.getInt("response") == 1) {
                        loginActivity.ok(response.getString("role"), response.getString("email"));
                    }
                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
        };
        queue.add(request);

    }


    public static void sendMail(Context context, String url, Cart cart, String to) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);


        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("TO", to);
        jsonObject1.put("TOTAL", (Utils.calcularSuma(cart.getOrders()) + "€"));

        JsonArray jsonArray = new JsonArray();
        for (Order product : cart.getOrders()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", product.getProduct().getName());
            jsonObject.addProperty("price", product.getProduct().getPrice());
            jsonObject.addProperty("imageUrl", product.getProduct().getUrl());
            jsonObject.addProperty("amount", product.getStock());
            jsonArray.add(jsonObject);
        }
        jsonObject1.put("products", jsonArray);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject1, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
        };
        queue.add(request);

    }

    public static void getTagsDescription(Context context, String url) {

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + AUTH, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String name = (object.getString("name"));
                        String desc = object.getString("description");


                        Provider provider = new Provider(name);
                        int pos = providers.indexOf(provider);
                        if (pos != -1) {
                            provider = providers.get(pos);
                        } else {
                            providers.add(provider);
                        }
                        provider.setType(desc.split(";;")[0]);
                        provider.setDesc(desc.split(";;")[1]);
                        provider.setUrlImage(desc.split(";;")[2]);

                    }

                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("Error");
            }
        });
        queue.add(request);
    }


    public static ArrayList<Category> setCategories(ArrayList<Product> products) {


        for (Product p : products) {
            Category category = new Category(p.getCategory());
            int pos = categories.indexOf(category);
            if (pos != -1) {
                category = categories.get(pos);
            } else {
                category.setProducts(new ArrayList<>());
                categories.add(category);
            }
            category.getProducts().add(p);
        }
        return categories;

    }

    public static ArrayList<Provider> setProviders(ArrayList<Product> products) {


        for (Product p : products) {
            Provider provider = new Provider(p.getProvider().getName());
            int pos = providers.indexOf(provider);

            if (pos != -1) {
                provider = providers.get(pos);
            } else {
                provider.setProducts(new ArrayList<>());
                providers.add(provider);
            }

            provider.getProducts().add(p);
            p.setProvider(provider);


        }
        return providers;

    }

    public static void reportProvider(AdapterCheckProvider adapterCheckProvider, int pos, Context context, String url, String user, String status) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject productJson = new JSONObject();
            productJson.put("user", user);
            productJson.put("status", status);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, productJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (status.equals("0")) {
                        adapterCheckProvider.removePosition(pos);
                        Toast.makeText(context, "Rechazado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Solicitado correctamente", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error al solicitar se proovedor", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);
        } catch (
                JSONException e) {
            Log.e("JSON Error", e.toString());
        }

    }

    public static void reportProviderComplete(Context context, String url, String user) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject productJson = new JSONObject();
            productJson.put("user", user);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, productJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(context, "Solicitud aprobada", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error al aprobar", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);
        } catch (
                JSONException e) {
            Log.e("JSON Error", e.toString());
        }

    }

    public static void registerUser(Context context, String url, String user, String mail, String pass) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject productJson = new JSONObject();
            productJson.put("user_login", user);
            productJson.put("user_pass", pass);
            productJson.put("user_email", mail);
            productJson.put("user_role", "customer");

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, productJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(context, "Registrado correctamente aprobada", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("BBDD", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USER", user);
                    editor.putString("PASS", pass);
                    editor.putString("EMAIL", mail);

                    editor.putString("ROLE", "customer");

                    editor.apply();

                    Intent   intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error al aprobar", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);
        } catch (
                JSONException e) {
            Log.e("JSON Error", e.toString());
        }

    }

    public static void listProviders(Context context, String url, RecyclerView recyclerView) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArray) {
                    AdapterCheckProvider adapterCheckProvider = new AdapterCheckProvider(Utils.listNames(jsonArray.toString()));
                    recyclerView.setAdapter(adapterCheckProvider);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error al aprobar", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);
        } catch (
                Exception e) {
        }

    }


}
