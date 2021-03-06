import { environment } from './../environments/environment.prod';
import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Post } from './models/post';
import { Category } from './models/category';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  // private baseUrl = 'http://localhost:8080/';
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl + 'api/posts/';
  // private categoryUrl = 'http://localhost:8080/api/categories';
  // private createUrl = 'http://localhost:8080/api/users/';
  // private catUrl = 'http://localhost:8080/api/user/';
  // private deleteUrl = 'http://localhost:8080/api/users/';
  // private updateUrl = 'http://localhost:8080/api/users/';

  getToken() {
    const token = this.authService.getToken();
    const headers = new HttpHeaders()
      .set('Authorization', `Basic ${token}`);
    return headers;
  }

  index() {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    console.log(headers);
    return this.http.get<Post[]>(this.url, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Index Error');
      })
    );
  }

  indexOfPostsByOtherUser(userId: number) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }
    const headers = this.getToken();
    return this.http.get<Post[]>(this.baseUrl + 'api/user/' + userId + '/posts', {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Index Other User Posts Error');
      })
    );
  }

  getPostByOtherUser(userId: number, postId: number) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }
    const headers = this.getToken();
    return this.http.get<Post>(this.baseUrl + 'api/user/' + userId + '/posts/' + postId, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Index Other User Posts Error');
      })
    );
  }

  getCommentsByPost(postId) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    return this.http.get<Post[]>(this.url + postId + '/comments/', {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Comments Post Error');
      })
    );
  }

  createPost(catId, post) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    return this.http.post(this.baseUrl + 'api/category/' + catId + '/posts', post, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Create Error');
      })
    );
  }

  createComment(pId, comment) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    return this.http.post(this.url + pId + '/comments', comment, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Create Error');
      })
    );
  }

  createCategory(userId, category) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }
    // catUrl = 'http://localhost:8080/api/user/'
    const headers = this.getToken();
    return this.http.post(this.baseUrl + 'api/user/' + userId + '/categories', category, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Create Category Error');
      })
    );
  }

  getCategories() {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    // categoryUrl = 'http://localhost:8080/api/categories'
    return this.http.get<Category[]>(this.baseUrl + 'api/categories', {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Category Error');
      })
    );
  }

  deletePost(userId, catId, postId) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }
    // deleteUrl = 'http://localhost:8080/api/users/'
    const headers = this.getToken();
    return this.http.delete(this.baseUrl + 'api/users/' + userId + '/category/' + catId + '/posts/' + postId, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Delete Error');
      })
    );
  }

  updatePost(userId, catId, post) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }
    // updateUrl = 'http://localhost:8080/api/users/'
    const headers = this.getToken();
    return this.http.put(this.baseUrl + 'api/users/' + userId + '/category/' + catId + '/posts/' + post.id, post, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Update Error');
      })
    );
  }

  updateComment(postId, comment) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    return this.http.put(this.url + postId + '/comments/' + comment.id, comment, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Update Commment Error');
      })
    );
  }

  deleteComment(postId, commentId) {
    console.log('made it to the post service delete');
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }
    console.log('made it to the post service delete');
    const headers = this.getToken();
    return this.http.delete(this.url + postId + '/comments/' + commentId, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Delete Comment Error');
      })
    );
  }

  addVote(commentId, vote) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    return this.http.post(this.baseUrl + 'api/comment/' + commentId + '/vote', vote, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Vote Error');
      })
    );
  }

  updateVote(commentId, vote) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    return this.http.put(this.baseUrl + 'api/comment/' + commentId + '/vote', vote, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Update Vote Error');
      })
    );
  }

  getVotesByComment(commentId, vote) {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('login');
    }

    const headers = this.getToken();
    return this.http.get(this.baseUrl + 'api/comment/' + commentId + '/' + vote, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Get Vote Error');
      })
    );
  }

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) { }
}
