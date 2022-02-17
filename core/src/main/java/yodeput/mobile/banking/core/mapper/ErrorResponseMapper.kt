/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package yodeput.mobile.banking.core.mapper

import yodeput.mobile.banking.common.util.ErrorCode
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * A mapper for mapping [ApiResponse.Failure.Error] response as custom [PokemonErrorResponse] instance.
 *
 * @see [ApiErrorModelMapper](https://github.com/skydoves/sandwich#apierrormodelmapper)
 */
object ErrorResponseMapper : ApiErrorModelMapper<ErrorCode> {

  /**
   * maps the [ApiResponse.Failure.Error] to the [PokemonErrorResponse] using the mapper.
   *
   * @param apiErrorResponse The [ApiResponse.Failure.Error] error response from the network request.
   * @return A customized [PokemonErrorResponse] error response.
   */

  override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorCode {
    val resStr: String = apiErrorResponse.errorBody!!.string()
    val json = JSONObject(resStr)
    val error = json.toMap()

    var message = ""
    if (error["error"] is String){
      message = json["error"] as String
    } else {
      val data = error["error"] as Map<String, String>
      message = data["name"] as String
    }

    return ErrorCode(apiErrorResponse.statusCode.code, message.replaceFirstChar { it.uppercase() })
  }
}

@Throws(JSONException::class)
fun JSONObject.toMap(): Map<String, Any> {
  val map = mutableMapOf<String, Any>()
  val keysItr: Iterator<String> = this.keys()
  while (keysItr.hasNext()) {
    val key = keysItr.next()
    var value: Any = this.get(key)
    when (value) {
      is JSONArray -> value = value.toList()
      is JSONObject -> value = value.toMap()
    }
    map[key] = value
  }
  return map
}

@Throws(JSONException::class)
fun JSONArray.toList(): List<Any> {
  val list = mutableListOf<Any>()
  for (i in 0 until this.length()) {
    var value: Any = this[i]
    when (value) {
      is JSONArray -> value = value.toList()
      is JSONObject -> value = value.toMap()
    }
    list.add(value)
  }
  return list
}
