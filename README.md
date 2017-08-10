# Send Authentication Information With VOLLEY Request

> You should override getHeaders, please try the following:

```sh
@Override
public Map<String, String> getHeaders() throws AuthFailureError {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Authorization", "Bearer " + CommonUtils.getInstance().getSharedPrefString(ServiceConstant.User.TOKEN));
    return headers;
}
```

