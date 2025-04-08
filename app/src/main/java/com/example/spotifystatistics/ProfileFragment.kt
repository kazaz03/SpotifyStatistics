package com.example.spotifystatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifystatistics.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //dummy data
        val artistList = mutableListOf(
            Artist("Adele", 122),
            Artist("The Weeknd", 2323),
            Artist("Taylor Swift", 33),
            Artist("Drake", 122)
        )
        val artistsAdapter=FollowedArtistsAdapter(artistList)
        binding.followedArtists.fArtistsList.layoutManager=LinearLayoutManager(requireContext())
        binding.followedArtists.fArtistsList.adapter=artistsAdapter

        //adding listener to items in recyclerview
        ItemClickSupport.addTo(binding.followedArtists.fArtistsList).setOnItemClickListener(
            object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {
                    val clickedArtist = artistList[position]
                    //Toast.makeText(context, "Clicked on ${clickedArtist.name}", Toast.LENGTH_SHORT).show()
                    openArtistInfoFragment(clickedArtist.name)
                    //TODO:Open fragment for info about artist
                }
            }
        )
    }

    private fun openArtistInfoFragment(name: String){
        //parent fragment manager because im already in a fragment that has its fm
        val fragmentManager=parentFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentPart,ArtistFragment())
            .setReorderingAllowed(true).addToBackStack(null).commit()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}