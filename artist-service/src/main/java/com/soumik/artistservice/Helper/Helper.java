package com.soumik.artistservice.Helper;

import com.soumik.artistservice.Exception.InvalidIdException;

public class Helper {

    public static Long convertId(String id)  {
        long fid;
         try {
              fid = Long.parseLong(id);
         } catch (NumberFormatException ex){
             throw new InvalidIdException();
         }
         return fid;
    }


}
