package com.charaminstra.pleon

//class HomeViewPagerFragment : Fragment() {
//    lateinit var binding: FragmentViewPagerBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = FragmentViewPagerBinding.inflate(LayoutInflater.from(context))
//
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View{
//        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
//        val viewPager = binding.viewPager
//
//        viewPager.adapter = PleonPagerAdapter(this)
//        viewPager.isUserInputEnabled = false
//
//        binding.bottomNavigation.itemIconTintList = null
//
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {}
//            override fun onPageSelected(position: Int) {
//                binding.bottomNavigation.menu.getItem(position).isChecked=true
////                when (position) {
////                    GARDEN_PAGE_INDEX -> findNavController().navigate(R.id.to_garden)
////                }
//            }
//        })
//
//
//
//        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_feed -> {
//                    binding.viewPager.currentItem = FEED_PAGE_INDEX
//                }
//                R.id.chat -> {
//                    binding.viewPager.currentItem = CHAT_PAGE_INDEX
//                }
//                R.id.doctor -> {
//                    binding.viewPager.currentItem = DOCTOR_PAGE_INDEX
//                }
//                R.id.nav_garden -> {
//                    binding.viewPager.currentItem = GARDEN_PAGE_INDEX
//                }
//                R.id.my -> {
//                    binding.viewPager.currentItem = MY_PAGE_INDEX
//                }
//                else -> {
//                    false
//                }
//            }
//            true
//        }
//        return binding.root
//    }
//}

