/*
 * Copyright (C) 2016. The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cloudkit.resources;

@Path("books")
public class Books {
    private final BookDao bookDao;
    public Books() {
        bookDao = new BookDao();
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Map<Integer,Book> getAllBooks(){
        return bookDao.getAll();
    }
    @Path("book")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Book getBook(@QueryParam("id")final int BookId){
        return bookDao.get(BookId);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Boolean insertBook(final Book bk)
    {
        return bookDao.update(bk);
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Boolean putBook(final Book bk){
        return bookDao.update(bk);
    }
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Boolean delBook(@QueryParam(("id"))final Integer bookId){
        return bookDao.delete(bookId);
    }
}
