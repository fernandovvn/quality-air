package br.edu.ufam.icomp.devtitans.qualityair.utils;

import java.util.Random;

public class Algorithm {

    static public int lower_bound(int[] a, int x){
        int l=0, r=a.length-1;
        while(l<=r){
            int mid = l + (r-l)/2;
            if(a[mid] >= x) r=mid-1;
            else l = mid+1;
        }
        return l;
    }

    static public int upper_bound(int[] a, int x){
        int l=0, r=a.length-1;
        while(l<=r){
            int mid = l + (r-l)/2;
            if(a[mid] > x) r=mid-1;
            else l = mid+1;
        }
        return l;
    }

    static public int randomRange(int mi, int mx) {
        Random random = new Random();
        return mi + random.nextInt(mx-mi+1);
    }

}
