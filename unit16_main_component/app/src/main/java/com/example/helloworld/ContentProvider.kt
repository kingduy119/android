package com.example.helloworld

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI("com.example.provider", "table3", 1)
    addURI("com.example.provider", "table3/#", 2)
}

private const val DBNAME = "database"

@Suppress("UNREACHABLE_CODE")
class ContentProvider : ContentProvider() {
//    val provider = "com.example.provider.College"
//    val url = "content://$provider/users"
//    val contentUrl = Uri.parse(url)

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? {
        var localSortOrder: String = sortOrder ?: ""
        var localSelection: String = selection ?: ""
        when(sUriMatcher.match(uri)) {
            1 -> { // If the incoming URI was for all of table3
                if(localSortOrder.isEmpty()) {
                    localSortOrder = "_ID ASC"
                }
            }
            2 -> { // If the incoming URI was for a single row
                localSelection += "_ID ${uri.lastPathSegment}"
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
