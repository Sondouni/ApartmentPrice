const searchFrmElem = document.querySelector('#searchFrm');
let showTableElem = document.querySelector('#showTable');

if(searchFrmElem){
    searchFrmElem.addEventListener('submit',(e)=>{
        let year = searchFrmElem.year.value;
        let month = searchFrmElem.month.value;
        let excd = searchFrmElem.excd.value;
        let locationcode = searchFrmElem.locationcode.value;
        e.preventDefault();
        fetch('/result',{
            'method': 'post',
            'headers': { 'Content-Type': 'application/json' },
            'body': JSON.stringify({year,month,excd,locationcode})
        }).then(res=>res.json())
            .then((data) =>{
                showTableElem.innerHTML = null;
                if(data.result){
                    let tableElem = makeTable();
                    data.result.forEach((item)=>{
                        let trElem = document.createElement('tr');
                        trElem.innerHTML = `
                            <td>${item.dong}</td>
                            <td>${item.jibun}</td>
                            <td>${item.apartmentname}</td>
                            <td>${item.dealamount}</td>
                            <td>${item.buildyear}</td>
                            <td>${item.dealyear}</td>
                            <td>${item.dealmonth}</td>
                            <td>${item.dealday}</td>
                            <td>${item.areaforexclusiveuse}</td>
                            <td>${item.floor}</td>
                            <td>${item.locanm}</td>
                        `;
                        tableElem.append(trElem);
                    });
                    showTableElem.append(tableElem);
                }else {
                    showTableElem.innerHTML='다른날짜를 검색해주세요';
                }
            });
    });
}
const makeTable = () => {
    const table = document.createElement('table');
    table.innerHTML = `
            <tr>
                <th>법정동</th>
                <th>지번</th>
                <th>아파트명</th>
                <th>거래금액</th>
                <th>건축년도</th>
                <th>계약년도</th>
                <th>계약월</th>
                <th>계약일</th>
                <th>전용면적</th>
                <th>층</th>
                <th>지역명</th>
            </tr>`;
    return table;
}
{
    //loca값 가져오기
    let excdSlElem = document.querySelector('#excdSl');
    let locationSlElem = document.querySelector('#locationSl');
    console.log(excdSlElem.selectedIndex);
    console.log(locationSlElem.value);
    excdSlElem.addEventListener('change',(e)=>{
        console.log(excdSlElem.selectedIndex);
        for (let childrenKey of locationSlElem.children) {
            if(excdSlElem.value==childrenKey.innerHTML){
                locationSlElem.value = childrenKey.value;
            }
        }
        console.log(locationSlElem.value);
    });
}