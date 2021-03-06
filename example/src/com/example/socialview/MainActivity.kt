package com.example.socialview

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.hendraanggrian.kota.content.getInteger
import com.hendraanggrian.socialview.commons.Hashtag
import com.hendraanggrian.socialview.commons.Mention
import com.hendraanggrian.widget.FilteredAdapter
import com.hendraanggrian.widget.HashtagAdapter
import com.hendraanggrian.widget.MentionAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Hendra Anggrian (com.hendraanggrian@gmail.com)
 */
class MainActivity : AppCompatActivity() {

    lateinit var defaultHashtagAdapter: ArrayAdapter<Hashtag>
    lateinit var defaultMentionAdapter: ArrayAdapter<Mention>
    lateinit var customHashtagAdapter: ArrayAdapter<Person>
    lateinit var customMentionAdapter: ArrayAdapter<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        defaultHashtagAdapter = HashtagAdapter(this)
        defaultHashtagAdapter.addAll(
                Hashtag(getString(R.string.hashtag1)),
                Hashtag(getString(R.string.hashtag2), getInteger(R.integer.hashtag2)),
                Hashtag(getString(R.string.hashtag3), getInteger(R.integer.hashtag3)))

        defaultMentionAdapter = MentionAdapter(this)
        defaultMentionAdapter.addAll(
                Mention(getString(R.string.mention1_username)),
                Mention(getString(R.string.mention2_username), getString(R.string.mention2_displayname), R.mipmap.ic_launcher),
                Mention(getString(R.string.mention3_username), getString(R.string.mention3_displayname), "https://avatars0.githubusercontent.com/u/11507430?v=3&s=460"))

        customHashtagAdapter = PersonAdapter(this)
        customHashtagAdapter.addAll(
                Person(getString(R.string.hashtag1)),
                Person(getString(R.string.hashtag2)),
                Person(getString(R.string.hashtag3)))

        customMentionAdapter = PersonAdapter(this)
        customMentionAdapter.addAll(
                Person(getString(R.string.mention1_username)),
                Person(getString(R.string.mention2_username)),
                Person(getString(R.string.mention3_username)))

        textView.threshold = 1
        textView.hashtagAdapter = defaultHashtagAdapter
        textView.mentionAdapter = defaultMentionAdapter
        textView.setHashtagTextChangedListener { _, s -> Log.d("editing", s.toString()) }
        textView.setMentionTextChangedListener { _, s -> Log.d("editing", s.toString()) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Default" -> {
                textView.hashtagAdapter = customHashtagAdapter
                textView.mentionAdapter = customMentionAdapter
                item.title = "Custom"
            }
            "Custom" -> {
                textView.hashtagAdapter = defaultHashtagAdapter
                textView.mentionAdapter = defaultMentionAdapter
                item.title = "Default"
            }
        }
        return super.onOptionsItemSelected(item)
    }

    data class Person constructor(val name: String)

    class PersonAdapter constructor(context: Context) : FilteredAdapter<Person>(context, R.layout.item_person, R.id.textViewName) {
        private var filter: Filter? = null

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val holder: ViewHolder
            var _convertView = convertView
            if (_convertView == null) {
                _convertView = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false)
                holder = ViewHolder(_convertView!!)
                _convertView.tag = holder
            } else {
                holder = _convertView.tag as ViewHolder
            }
            val model = getItem(position)
            if (model != null) {
                holder.textView.text = model.name
            }
            return _convertView
        }

        override fun getFilter(): Filter {
            if (filter == null)
                filter = object : SocialFilter() {
                    override fun convertResultToString(resultValue: Any): CharSequence {
                        return (resultValue as Person).name
                    }
                }
            return filter as Filter
        }

        private class ViewHolder constructor(view: View) {
            val textView = view.findViewById<TextView>(R.id.textViewName)!!
        }
    }
}