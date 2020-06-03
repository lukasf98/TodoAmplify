package com.mobileappdevelopment.todoamplify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.DataStoreChannelEventName
import com.amplifyframework.datastore.appsync.ModelWithMetadata
import com.amplifyframework.datastore.generated.model.Task
import com.amplifyframework.hub.HubChannel

class ShowTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_todo)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "API Demo"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val todoLog = findViewById<TextView>(R.id.todos)

        Amplify.DataStore.query(
            Task::class.java,
            { todos ->
                while (todos.hasNext()) {
                    val todo = todos.next()
                    todoLog.append("\n" + todo.toString())
                }
            },
            { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
        )

        Amplify.Hub.subscribe(
            HubChannel.DATASTORE,
            { event -> event.getName() == DataStoreChannelEventName.RECEIVED_FROM_CLOUD.toString() },
            { event ->
                val modelWithMetadata = event.getData() as ModelWithMetadata<*>?
                val todo: Task = modelWithMetadata!!.model as Task

                todoLog.append("\n" + todo.toString())
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
