package com.example.youtubeklonapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.YoutubeKlonApplication
import com.example.youtubeklonapp.entitiy.VideoEntitiy
import com.example.youtubeklonapp.viewmodel.VideoPlayerViewModel
import com.example.youtubeklonapp.viewmodel.ViewModelFactory

class VideoPlayerButtonFragment : Fragment() {
    lateinit var btnPlayer: Button
    lateinit var btnFavorite : ImageButton

    var uniqueCount : Int = 0

    private val viewModel: VideoPlayerViewModel by viewModels {
        ViewModelFactory((requireActivity().application  as YoutubeKlonApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.video_player_button_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFavorite.setOnClickListener {
            val LiveDataSelectedVideo = viewModel.getVideoById(VideoPlayerActivity.VIDEO_ID)
            if (LiveDataSelectedVideo != null) {
                LiveDataSelectedVideo.observe(viewLifecycleOwner, {data ->
                    data?.let {
                        viewModel.delete(data)
                        btnFavorite.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_baseline_favorite_border_24))
                    }
                })
            }
            else{
                val videoEntitiy = VideoEntitiy(uniqueCount++, VideoPlayerActivity.VIDEO_ID,"true")//uniquecount yerine shared peferences kullanılabilir
                viewModel.insert(videoEntitiy)
                btnFavorite.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_baseline_favorite_24))
            }
        }
    }
}