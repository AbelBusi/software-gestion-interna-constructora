function openGallery(imageArray) {
    let galleryWindow = window.open('', '_blank');
    galleryWindow.document.write('<html><head><title>Galería</title></head><body style="background:#000; color:#fff; display:flex; flex-wrap:wrap; justify-content:center;">');
  
    imageArray.forEach(url => {
      galleryWindow.document.write(`<img src="${url}" style="width:300px; margin:10px; border-radius:10px;" />`);
    });
  
    galleryWindow.document.write('</body></html>');
  }
  